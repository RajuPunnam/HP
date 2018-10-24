package com.io.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="eo_hp_l06_l10_data")
public class EoCommodityPojo {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="id",unique = true, nullable = false)
	private int id;
	
	@Column(name="Root Cause")
	private String root_Cause;	
	
	@Column(name="Qty")
	private double qty;	
	
	@Column(name="PL")
	private String pl;	
	
	@Column(name="`PCA/BOX`")
	private String pca_box;	
	
	@Column(name="PartHP")
	private String partHP;	
	
	@Column(name="PartFLEX")
	private String partFLEX;	
	
	@Column(name="`Onwer Mitigacao`")
	private String onwer_Mitigacao;	
	
	@Column(name="Nivel")
	private int nivel;	
	
	@Column(name="ISSUE")
	private String issue;	
	
	@Column(name="FileDate")
	private Date fileDate;	
	
	@Column(name="Family2")
	private String family2;	
	
	@Column(name="Family")
	private String family;	
	
	@Column(name="`Exposure $ (160%)`")
	private double exposure_$;
	
	@Column(name="Exposure")
	private String exposure;
	
	@Column(name="`Due Date`")
	private String due_Date;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Demand")
	private String demand;
	
	@Column(name="`Data Inicio Accrual`")
	private Date data_Inicio_Accrual;
	
	@Column(name="`Commodity Manager`")
	private String commodity_Manager;
	
	@Column(name="Commodity")
	private String commodity;
	
	@Column(name="`Comentarios Pivot Table`")
	private String comentarios_Pivot_Table;
	
	@Column(name="Comentarios")
	private String comentarios;
	
	@Column(name="`Aging>1Year`")
	private String agingGT1Year;
	
	@Column(name="Aging")
	private double aging;	
	
	@Column(name="`Acao de mitigacao`")
	private String acao_de_mitigacao;	
	
	@Column(name="`>1Year`")
	private String GT1Year;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoot_Cause() {
		return root_Cause;
	}
	public void setRoot_Cause(String root_Cause) {
		this.root_Cause = root_Cause;
	}
	
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public void setExposure_$(double exposure_$) {
		this.exposure_$ = exposure_$;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getPca_box() {
		return pca_box;
	}
	public void setPca_box(String pca_box) {
		this.pca_box = pca_box;
	}
	public String getPartHP() {
		return partHP;
	}
	public void setPartHP(String partHP) {
		this.partHP = partHP;
	}
	public String getPartFLEX() {
		return partFLEX;
	}
	public void setPartFLEX(String partFLEX) {
		this.partFLEX = partFLEX;
	}
	public String getOnwer_Mitigacao() {
		return onwer_Mitigacao;
	}
	public void setOnwer_Mitigacao(String onwer_Mitigacao) {
		this.onwer_Mitigacao = onwer_Mitigacao;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getFamily2() {
		return family2;
	}
	public void setFamily2(String family2) {
		this.family2 = family2;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	
	public String getExposure() {
		return exposure;
	}
	public void setExposure(String exposure) {
		this.exposure = exposure;
	}
	public String getDue_Date() {
		return due_Date;
	}
	public void setDue_Date(String due_Date) {
		this.due_Date = due_Date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public Date getData_Inicio_Accrual() {
		return data_Inicio_Accrual;
	}
	public void setData_Inicio_Accrual(Date data_Inicio_Accrual) {
		this.data_Inicio_Accrual = data_Inicio_Accrual;
	}
	public String getCommodity_Manager() {
		return commodity_Manager;
	}
	public void setCommodity_Manager(String commodity_Manager) {
		this.commodity_Manager = commodity_Manager;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getComentarios_Pivot_Table() {
		return comentarios_Pivot_Table;
	}
	public void setComentarios_Pivot_Table(String comentarios_Pivot_Table) {
		this.comentarios_Pivot_Table = comentarios_Pivot_Table;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getAgingGT1Year() {
		return agingGT1Year;
	}
	public void setAgingGT1Year(String agingGT1Year) {
		this.agingGT1Year = agingGT1Year;
	}
	
	
	public double getAging() {
		return aging;
	}
	public void setAging(double aging) {
		this.aging = aging;
	}
	public double getExposure_$() {
		return exposure_$;
	}
	public String getAcao_de_mitigacao() {
		return acao_de_mitigacao;
	}
	public void setAcao_de_mitigacao(String acao_de_mitigacao) {
		this.acao_de_mitigacao = acao_de_mitigacao;
	}
	public String getGT1Year() {
		return GT1Year;
	}
	public void setGT1Year(String gT1Year) {
		GT1Year = gT1Year;
	}
	@Override
	public String toString() {
		return "EoCommodityPojo [id=" + id + ", root_Cause=" + root_Cause
				+ ", qty=" + qty + ", pl=" + pl + ", pca_box=" + pca_box
				+ ", partHP=" + partHP + ", partFLEX=" + partFLEX
				+ ", onwer_Mitigacao=" + onwer_Mitigacao + ", nivel=" + nivel
				+ ", issue=" + issue + ", fileDate=" + fileDate + ", family2="
				+ family2 + ", family=" + family + ", exposure_$=" + exposure_$
				+ ", exposure=" + exposure + ", due_Date=" + due_Date
				+ ", description=" + description + ", demand=" + demand
				+ ", data_Inicio_Accrual=" + data_Inicio_Accrual
				+ ", commodity_Manager=" + commodity_Manager + ", commodity="
				+ commodity + ", comentarios_Pivot_Table="
				+ comentarios_Pivot_Table + ", comentarios=" + comentarios
				+ ", agingGT1Year=" + agingGT1Year + ", aging=" + aging
				+ ", acao_de_mitigacao=" + acao_de_mitigacao + ", GT1Year="
				+ GT1Year + "]";
	}	

}
